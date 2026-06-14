/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * A411_collaboration - User Controller Class
 */
package controller;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuthDao;
import model.User;
import model.ResponseModel;
import java.util.ArrayList;

/**
 *
 * @author Amir
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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
        
        //respon berupa data json
        response.setContentType("application/json");
        //mengambil nilai parameter page
        String page = request.getParameter("page");
        //menampilkan data
        PrintWriter out = response.getWriter();
        //membuat objek auth dao
        AuthDao authdao = new AuthDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<User> listUser = authdao.getAllUser();
            //convert to json
            String jsonSiswa = gson.toJson(listUser);
            //tampilkan data
            out.println(jsonSiswa);
        }
        
        // Amir_collaboration - Daftar User
        else if(page.equals("tambah")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String nohp = request.getParameter("nohp");
            String alamat = request.getParameter("alamat");
            
            if(authdao.getRecordByEmail(email).getEmail()!= null)
            {
                response.setContentType("application/json");
                
                out.println(gson.toJson("sudah_terdaftar"));
            }
            else
            {
                User user = new User();
                user.setRole(0);
                user.setEmail(request.getParameter("email"));
                user.setUsername(request.getParameter("username"));
                user.setNohp(request.getParameter("nohp"));
                user.setAlamat(request.getParameter("alamat"));
                user.setPassword(request.getParameter("password"));
                
                authdao.simpanData(user, page);
                
                response.setContentType("application/json");
                out.println(gson.toJson("berhasil_terdaftar"));
            }
        }
        else if(page.equals("tampil")){
            String jSonSiswa = gson.toJson(authdao.getRecordByEmail(request.getParameter("email")));
            response.setContentType("application/json");
            out.println(jSonSiswa);
        }
        else if(page.equals("edit"))
        {
            User user = new User();
            user.setRole(0);
            user.setEmail(request.getParameter("email"));
            user.setUsername(request.getParameter("username"));
            user.setNohp(request.getParameter("nohp"));
            user.setAlamat(request.getParameter("alamat"));
            user.setPassword(request.getParameter("password"));
            
            authdao.simpanData(user, page);
            response.setContentType("application/json");
            out.println(gson.toJson("berhasil_diupdate"));  
        }
        else if(page.equals("hapus")){
            authdao.hapusData(request.getParameter("email"));
            response.setContentType("application/json");
            out.println(gson.toJson("berhasil_dihapus"));  
        } else if (page.equals("login")) {
            response.setContentType("application/json");
            
            
            User user = authdao.getRecordByEmail(request.getParameter("email"));
            ResponseModel myResponse = new ResponseModel();
            
            
            if(!request.getParameter("password").isEmpty() && !request.getParameter("email").isEmpty()) {
                if(user.getEmail() != null && !user.getEmail().isEmpty()) {
                    if(user.getPassword().equals(request.getParameter("password"))) {
                        myResponse.setMessage("berhasil_login");
                        myResponse.setData(user);
                        out.println(gson.toJson(myResponse));
                    } else {
                        myResponse.setMessage("password_salah");
                        out.println(gson.toJson(myResponse));
                    }
                } else {
                    myResponse.setMessage("belum_terdaftar");
                    out.println(gson.toJson(myResponse));
                }
            } else {
                myResponse.setMessage("empty_field");
                out.println(gson.toJson(myResponse));
            }
            
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
