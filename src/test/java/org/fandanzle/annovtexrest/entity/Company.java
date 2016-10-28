package org.fandanzle.annovtexrest.entity;

import org.fandanzle.annovtexrest.annotation.*;

import java.util.Date;

/**
 * Java pojo class for clients
 * Created by alexb on 18/11/2015.
 */
@RequestMapping(
        collectionName = "test_company_collection"
)
public class Company {

    // You need to declare a class varible as an @DocumentFiel
    @Id(indexName = "_id")
    @DocumentField(required = true)
    private String _id;

    @DocumentField(required = true)
    @UniqueIndex(indexName = "name_unique_index")
    private String name;

    @DocumentField(required = true)
    @UniqueIndex(indexName = "dob_unique_index")
    private Date dob;

    @DocumentField(required = true)
    @UniqueIndex(indexName = "height_unique_index")
    private String height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}