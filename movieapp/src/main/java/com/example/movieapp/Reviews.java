package com.example.movieapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
    @Id
    private ObjectId id;
    private String body;
    private String imdbId;
    private String username;

    public Reviews(String reviewbody,String username,String imdbId) {
        this.body=reviewbody;
        this.username=username;
        this.imdbId=imdbId;
    }

}
