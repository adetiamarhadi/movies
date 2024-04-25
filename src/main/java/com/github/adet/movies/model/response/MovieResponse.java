package com.github.adet.movies.model.response;

public class MovieResponse {

    private int id;
    private String title;
    private String description;
    private double rating;
    private String image;
    private long createdAt;
    private long updatedAt;

    public MovieResponse(int id, String title, String description, double rating, String image, long createdAt, long updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
