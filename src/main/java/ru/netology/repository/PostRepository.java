package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> allPosts = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(allPosts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(allPosts.get(id));
    }

    public Post save(Post post) {
        long postId = post.getId();
        if (postId == 0) {
            id.getAndIncrement();
            post.setId(id.get());
            allPosts.put(id.get(), post);
        }
        if (postId != 0) {
            if (allPosts.containsKey(postId)) {
                allPosts.put(postId, post);
                return post;
            } else {
                throw new NotFoundException("Post with ID --> " + postId + " <-- not found");
            }
        }
        return post;
    }

    public void removeById(long id) {
        if(!allPosts.containsKey(id)){
            throw new NotFoundException("Post with ID --> " + id + " <-- not found");
        } else {
            allPosts.remove(id);
        }
    }
}
