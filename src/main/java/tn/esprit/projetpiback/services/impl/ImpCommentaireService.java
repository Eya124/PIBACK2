package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import tn.esprit.projetpiback.entites.Commentaire;
import tn.esprit.projetpiback.entites.Post;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.repository.CommentaireRepository;
import tn.esprit.projetpiback.repository.PostRepository;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.CommentaireService;
import tn.esprit.projetpiback.services.PostService;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ImpCommentaireService implements CommentaireService {

    private final PostRepository postRepository;
    private final CommentaireRepository commentaireRepository;
    private final UsersRepository usersRepository;

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

    @Override
    @Transactional
    public void ajouterCommentaire(Commentaire c, Integer idUser, Integer idPost) {
        Post post = postRepository.findById(idPost).orElse(null);
        User user = usersRepository.findById(idUser).orElse(null);
        Assert.notNull(post, "Entity must not be null.");
        Assert.notNull(user, "Entity must not be null.");
        commentaireRepository.saveAndFlush(c);
        c.setPost(post);
        c.setUsercommentaire(user);
    }

    @Override
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void filterCommentaire() {

        File badlist = new File("C:\\Users\\Firas\\Desktop\\bad.txt");
    //   URL fileUrlbadlist = getClass().getResource("src/main/java/tn.esprit.projectpiback/lists/bad.txt");
    //   File badlist = new File(fileUrlbadlist.getFile());
        List<String> badWords = loadWordList(badlist);
        List<Commentaire> allComments = commentaireRepository.findAll();

        for (Commentaire commentaire : allComments) {
            System.out.println(commentaire.getContent());
            String filteredText = commentaire.getContent();
            for (String word : badWords) {
                filteredText = filteredText.replaceAll("(?i)\\b" + word + "\\b", "*****");
            }
            commentaire.setContent(filteredText);
        }
        commentaireRepository.saveAll(allComments);
    }

    @Override
    public List<Commentaire> getComentairePost(int idPost) {
        Post post = postRepository.findById(idPost).orElse(null);
        Assert.notNull(post, "Entity must not be null.");
        List<Commentaire> allComments = post.getCommentaires();
        return allComments;
    }


}