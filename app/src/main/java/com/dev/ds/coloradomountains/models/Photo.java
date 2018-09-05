package com.dev.ds.coloradomountains.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Photo implements Parcelable {
    @Expose
    public String title;
    @Expose
    public String link;

    @Expose
    @SerializedName("date_taken")
    public String dateTaken;
    @Expose
    public String author;
    @Expose
    public String tags;
    @Expose
    public Media media;

    public Photo() {}

    public String getReadableDateTaken() {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {

            Date date = format.parse(dateTaken);

            return date.toString();
        } catch (ParseException e) {
            return "";
        }
    }

    // Get a comma separated list of the first 10 tags of the photo.
    public String getTagsDescription() {
        String[] tagList = tags.split(" ");
        StringBuilder list = new StringBuilder("Tags: ");
        int numTags = Math.min(tagList.length, 10);
        for(int i = 0; i < numTags; i++) {
            list.append(tagList[i]);
            if (i < numTags - 1) {
                list.append(", ");
            }
        }
        return list.toString();
    }

    // The author field from Flickr usually includes a generic email, this function sanitizes the email and only includes the name
    public String getAuthorDescription() {
        String[] splitByQuotes = author.split("\"");
        if (splitByQuotes.length > 1) {
            return splitByQuotes[1];
        }
        return author;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(link);
        out.writeString(dateTaken);
        out.writeString(author);
        out.writeString(tags);
        out.writeString(media.photoUrl);
    }

    public static final Parcelable.Creator<Photo> CREATOR
            = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel in) {
        title = in.readString();
        link = in.readString();
        dateTaken = in.readString();
        author = in.readString();
        tags = in.readString();
        media = new Media();
        media.photoUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

}
