package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.projetpiback.entites.Commentaire;
import tn.esprit.projetpiback.entites.Feedback;
import tn.esprit.projetpiback.entites.Post;
import tn.esprit.projetpiback.repository.CommentaireRepository;
import tn.esprit.projetpiback.repository.FeedbackRepository;
import tn.esprit.projetpiback.repository.PostRepository;
import tn.esprit.projetpiback.services.FeedbackService;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ImpFeedbackService implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final PostRepository postRepository;
    private final CommentaireRepository commentaireRepository;

    public List<String> loadWordList(File file) {
        List<String> wordList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.trim());
            }
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log an error, throw an exception)
            e.printStackTrace();
        }

        return wordList;
    }
    @Scheduled(fixedRate = 30000)
    @Transactional
    @Override
    public void processComment() {

        System.out.println("entering feedback section");
        File PostiveWordlist = new File("C:\\Users\\ibrah\\OneDrive\\Desktop\\ESPRIT\\PI\\Positivewordlist.txt");
        File NegativeWordlist = new File("C:\\Users\\ibrah\\OneDrive\\Desktop\\ESPRIT\\PI\\Negativewordlist.txt");

        List<String> positiveWords = loadWordList(PostiveWordlist);
        List<String> negativeWords = loadWordList(NegativeWordlist);


        //traitement de commentaire

        //it works but i should affect every comment to 1 feedback id
        List<Post> allPosts = postRepository.findAll();
        List<Commentaire> allComments = new ArrayList<>();
        for (Post p:allPosts) {
            for (Commentaire com: p.getCommentaires()) {
                allComments.add(com);
            }
        }

        for (Commentaire comment : allComments) {
            String content = comment.getContent();
            int feedbackNumber = 0;

            // Check for positive words
            for (String positiveWord : positiveWords) {
                if (content.contains(positiveWord)) {
                    feedbackNumber++;  // Increment the feedback number for each positive word found
                }
            }

            // Check for negative words
            for (String negativeWord : negativeWords) {
                if (content.contains(negativeWord)) {
                    feedbackNumber--;  // Decrement the feedback number for each negative word found
                }
            }


            //check if the comment already has a feedback
            Feedback fd = feedbackRepository.findByCommentairefdIdCommentaire(comment.getIdCommentaire());
            if (fd == null) {
                // Create and set the feedback entity
                Feedback feedback = new Feedback();
                feedback.setFeedbackNumber(feedbackNumber);
                comment.setFeedback(feedback);
                feedback.setCommentairefd(comment);
                feedbackRepository.saveAndFlush(feedback);
                commentaireRepository.saveAndFlush(comment);
            }
            else {

                fd.setFeedbackNumber(feedbackNumber);
                comment.setFeedback(fd);
                feedbackRepository.saveAndFlush(fd);
                commentaireRepository.saveAndFlush(comment);

            }
        }

        //process post
        for (Post post :allPosts) {
            //list that contains feedback number of each post
            List<Integer> allfeedbacknumbers = new ArrayList<>();
            // the rating of the post
            Integer rating = 0;
            for (Commentaire commentaire: post.getCommentaires()) {
                // get the feedbacks of comments of each post
                Feedback feedbackofpost =  feedbackRepository.findByCommentairefdIdCommentaire(commentaire.getIdCommentaire());
                allfeedbacknumbers.add(feedbackofpost.getFeedbackNumber());
            }
            for (Integer i: allfeedbacknumbers) {
                rating += i;
            }
            System.out.println(rating + post.getDescription());

            //check if the post has already a rating
            Feedback postfd = feedbackRepository.findByPostfdIdPost(post.getIdPost());
            if (postfd == null){
                Feedback feedbackpst = new Feedback();
                feedbackpst.setFeedbackNumber(rating);
                feedbackpst.setPostfd(post);
                post.setFeedback(feedbackpst);
                feedbackRepository.saveAndFlush(feedbackpst);
                postRepository.saveAndFlush(post);
            } else {
                postfd.setPostfd(post);
                postfd.setFeedbackNumber(rating);
                post.setFeedback(postfd);
                feedbackRepository.saveAndFlush(postfd);
                postRepository.saveAndFlush(post);
            }
        }

    }



}


