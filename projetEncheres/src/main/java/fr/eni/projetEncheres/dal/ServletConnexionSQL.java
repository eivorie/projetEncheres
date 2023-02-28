package fr.eni.projetEncheres.dal;

import javax.naming.Context;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletConnexionSQL", value = "/ServletConnexionSQL")
public class ServletConnexionSQL extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            Context context = new InitialContext();
            DataSource datasource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
            Connection cnx = datasource.getConnection();
            out.print("La connexion est " + (cnx.isClosed()?"fermée":"ouverte") + ".");
            cnx.close();
            out.print("La connexion est " + (cnx.isClosed()?"fermée":"ouverte") + ".");
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Une erreur est survenue lors de l'utilisation de la base de données : " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
