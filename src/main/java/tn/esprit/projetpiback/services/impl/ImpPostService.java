package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.webjars.NotFoundException;
import tn.esprit.projetpiback.entites.*;
import tn.esprit.projetpiback.repository.CommentaireRepository;
import tn.esprit.projetpiback.repository.NotificationRepository;
import tn.esprit.projetpiback.repository.PostRepository;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.PostService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class ImpPostService implements PostService {

    private final PostRepository postRepository;
    private final CommentaireRepository commentaireRepository;
    private final UsersRepository usersRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void ajouterPost(Post p, Integer idUser ) {
        User utilisateur = usersRepository.findById(idUser).orElse(null); // Remplacez userId par l'ID de l'utilisateur concerné
        if (utilisateur != null) {
            p.setArchive(false);
            p.setUserpost(utilisateur);
            postRepository.save(p);
        }/*
        List<User> usersInteresses = usersRepository.findUsersInteresses();
        for (User user : usersInteresses) {
            Notification notification = Notification.builder()
                    .content("Un nouveau post a été ajouté : " + p.getDescription())
                    .createdAt(LocalDate.now())
                    .type(NotificationType.NOUVEAU_POST)
                    // Autres informations pertinentes
                    .build();

           // notification.setUser(user);
            notificationRepository.save(notification);
            user.getNotifications().add(notification);*/
       // }
    }

    @Override
    public void updatePost(Post p) {
        postRepository.save(p);

    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post getByIdPost(Integer id) {
        return postRepository.findById(id).orElse(null);
    }
    public List<Post> getAllNotArchivedPosts() {
        return postRepository.findAllNotArchived();
    }

    @Override
    public void deletePost(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setArchive(true); // Mettre à jour le statut du post en "archivé"
            postRepository.save(post); // Enregistrer les modifications dans la base de données
        } else {
            // Gérer le cas où le post n'est pas trouvé
            throw new NotFoundException("Post not found");
        }
    }

//    @Override
//    @Transactional
//    public void addCommentToPost(Integer idPost,Integer idUser, Commentaire commentaire) {
//        // Logique pour ajouter le commentaire au post
//        Post post = postRepository.findById(idPost).orElse(null);
//        User user = usersRepository.findById(idUser).orElse(null);
//        if (post != null && user != null) {
//            //commentaireRepository.saveAndFlush(commentaire);
//            commentaire.setPost(post);
//            commentaire.setUser(user);
//            /*post.getCommentaires().add(commentaire);
//            postRepository.save(post);*/
//            commentaireRepository.save(commentaire);
//        }

        // Création et association d'une notification pour les utilisateurs intéressés
//        Post postN = commentaire.getPost();
//        List<User> usersInteresses = usersRepository.findUsersInteresses();
//        for (User user : usersInteresses) {
//            Notification notification = Notification.builder()
//                    .content("Un nouveau commentaire a été ajouté au post : " + post.getDescription())
//                    .createdAt(LocalDate.now())
//                    .type(NotificationType.NOUVEAU_COMMENTAIRE)
//                    // Autres informations pertinentes
//                    .build();
//
//            notification.setUser(user);
//            notificationRepository.save(notification);
//            user.getNotifications().add(notification);
//        }
    }

//    @Override
//    public void addReplyToComment(Integer idCommentaire, Commentaire reply) {
//        // Logique pour ajouter la réponse au commentaire
//        Commentaire commentaire = commentaireRepository.findById(idCommentaire).orElse(null);
//        if (commentaire != null) {
//            reply.setParentComment(commentaire);
//            commentaire.getReplies().add(reply);
//            commentaireRepository.save(commentaire);
//        }
//
//        // Création et association d'une notification pour les utilisateurs intéressés
//        //Commentaire commentaireN = reply.getCommentaire();
//        List<User> usersInteresses = usersRepository.findUsersInteresses();
//        for (User user : usersInteresses) {
//            Notification notification = Notification.builder()
//                    .content("Une nouvelle réponse a été ajoutée à un commentaire")
//                    .createdAt(LocalDate.now())
//                    .type(NotificationType.NOUVELLE_REPONSE)
//                    // Autres informations pertinentes
//                    .build();
//
//            notification.setUser(user);
//            user.getNotifications().add(notification);
//            notificationRepository.save(notification);
//        }
//    }

//    @Override
//    public void likePost(Integer postId, Integer idUser) {
//        Post post = postRepository.findById(postId).orElse(null);
//        if (post == null) {
//            throw new PostNotFoundException("Post not found");
//        }
//
//        // Vérifier si l'utilisateur a déjà aimé le post
//        boolean alreadyLiked = post.getNbrLikes().contains(idUser);
//
//        if (alreadyLiked) {
//            // Si l'utilisateur a déjà aimé, retirer son like en décrémentant le nombre de likes
//            post.setNbrLikes(post.getNbrLikes() - 1);
//            post.getNbrLikes().remove(idUser);
//        } else {
//            // Si l'utilisateur n'a pas encore aimé, ajouter son like en incrémentant le nombre de likes
//            post.setNbrLikes(post.getNbrLikes() + 1);
//            post.getNbrLikes().add(idUser);
//        }
//
//        postRepository.save(post);
//    }
//    }

