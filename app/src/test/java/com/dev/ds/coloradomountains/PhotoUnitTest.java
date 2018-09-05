package com.dev.ds.coloradomountains;

import com.dev.ds.coloradomountains.models.Photo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhotoUnitTest {

    @Test
    public void photo_tags_description() {
        Photo photo = new Photo();
        photo.tags = "hello this is a list of tags";
        assertEquals("Tags: hello, this, is, a, list, of, tags", photo.getTagsDescription());



        photo.tags = "hello this is a list of tags with several extra tags that will be cut off";
        assertEquals("Tags: hello, this, is, a, list, of, tags, with, several, extra", photo.getTagsDescription());
    }

    @Test
    public void photo_date_taken_description() {
        Photo photo = new Photo();
        photo.dateTaken = "2018-08-11T17:47:05-08:00";

        assertEquals("Thu Jan 11 17:47:05 CST 2018", photo.getReadableDateTaken());
    }

    @Test
    public void photo_author_description() {
        Photo photo = new Photo();
        photo.author = "Lakewood Colorado";

        assertEquals("Lakewood Colorado", photo.getAuthorDescription());


        // Make sure email is removed.
        photo.author = "email@gmail.com \"Lakewood Colorado\"";
        assertEquals("Lakewood Colorado", photo.getAuthorDescription());
    }


}
