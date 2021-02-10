package com.tp.bakery.persistence;

import com.tp.bakery.model.Dessert;
import com.tp.bakery.persistence.mappers.BakeryMapper;
import com.tp.bakery.persistence.mappers.IntegerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostgresBakeryDAO implements BakeryDAO{
    @Autowired
    JdbcTemplate template;

    @Override
    public List<Dessert> getAllDesserts() {
        List<Dessert> allDesserts=template.query("select \"dessertId\", \"dessertName\", \"dessertDescription\" from \"Desserts\";",new BakeryMapper());
        return allDesserts;
    }

    @Override
    public Dessert addDessert(Dessert dessert) {
        Integer dessertId=template.queryForObject("insert into \"Desserts\" (dessertId\", \"dessertName\", \"dessertDescription\")\n" +
                "values (?,?,?) returning \"dessertId\";",new IntegerMapper("dessertId"),dessert.getName(),dessert.getDescription()) ;

        dessert.setDessertId(dessertId);

        return dessert;
    }

    @Override
    public Dessert getDessertById(Integer dessertId) {

        Dessert retreived=template.queryForObject("select \"dessertId\", \"dessertName\", \"dessertDescription\" from \"Desserts\" where \"dessertId\"='"+dessertId+"'",new BakeryMapper());

        return retreived;

    }

    @Override
    public int editDessert(Integer dessertId, Dessert editdessert) {
        int edited=template.update("update \"Desserts\"\n" +
                        "set \"dessertName\"=?, \"dessertDescription\"=?\n" +
                        "where \"dessertId\"=?;\n" +
                        "\n",

                editdessert.getName(),editdessert.getDescription(),dessertId);

        return edited;

    }

    @Override
    public int deleteDessert(Integer dessertId) {
        int deleted=template.update("delete from \"Desserts\" where \"dessertId\"=?;",dessertId);
        return deleted;

    }



    }



