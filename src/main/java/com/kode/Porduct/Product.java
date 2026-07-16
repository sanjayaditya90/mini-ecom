package com.kode.Porduct;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product {

    private Long id;
    private String name;
    private String category;
    private int price;
    private int stock;
    private String brand;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct() throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom_kode_jdbc",
                "root",
                "root"
        );

        connection.setAutoCommit(false);

        try {


//            BufferedReader br = new BufferedReader(new FileReader("product.csv"));
            InputStream is = getClass().getClassLoader().getResourceAsStream("product.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String addQuery = "INSERT INTO products(id, name, category, price, stock, brand) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";


            PreparedStatement ps = connection.prepareStatement(addQuery);

            String line;
            br.readLine();

            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] product = line.split(",");

                ps.setLong(1, Long.parseLong(product[0]));
                ps.setString(2, product[1]);
                ps.setString(3, product[2]);
                ps.setInt(4, Integer.parseInt(product[3]));
                ps.setInt(5, Integer.parseInt(product[4]));
                ps.setString(6, product[5]);

                ps.addBatch();
                count++;

                if (count % 20 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }

//            ps.execute();
            connection.commit();
            br.close();
            ps.close();
            System.out.println("Products added successfully");
        }
        catch (Exception e) {
            System.err.println("Unable to add data");
            e.printStackTrace();
        }

    }
}
