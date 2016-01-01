/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vaibhav
 */
public class City_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet City_Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet City_Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            try{
            String JDBC_driver="com.mysql.jdbc.Driver";
            String DB_URL="jdbc:mysql://localhost:3306/wheels_on_rent";
            Class.forName(JDBC_driver);
            String db_user="root";
            String db_pass="";
            
            Connection conn=DriverManager.getConnection(DB_URL, db_user, db_pass);
            String query="SELECT `City` FROM `india_states_cities` WHERE `State/Union Territory` == `Sikkim`";
            PreparedStatement pst =conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
             String json;
            while(rs.next())
            {
                City c= new City();
                c.setCity_name(rs.getString("City"));
                //s.setCity_name(rs.getString("City"));
                Gson g= new Gson();
              
                json=g.toJson(c);
                out.print(json);
            }
            
           
            
            
            rs.close();
            pst.close();
            conn.close();}
            catch(Exception ex)
        {
            out.print("error"+ex.getMessage());
        }
        } catch (IOException ex) {
            Logger.getLogger(State_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
