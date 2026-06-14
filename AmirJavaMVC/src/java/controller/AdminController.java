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
import dao.AdminDao;
import model.User;
import model.ResponseModel;
import model.LokasiModel;
import java.util.ArrayList;
import model.ListPresensiModel;

/**
 *
 * @author Amir
 */
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

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
        AdminDao admindao = new AdminDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<User> listUser = authdao.getAllUser();
            //convert to json
            String jsonSiswa = gson.toJson(listUser);
            //tampilkan data
            out.println(jsonSiswa);
        }
        
        else if(page.equals("tambahLokasi")) {
            String namaLokasi = request.getParameter("namaLokasi");
            String kodeLokasi = request.getParameter("kodeLokasi");
            
            LokasiModel lokasi = new LokasiModel();
            
            lokasi.setNamaLokasi(namaLokasi);
            lokasi.setKodeLokasi(kodeLokasi);
            
            admindao.simpanData(lokasi, page);
            
            response.setContentType("application/json");
                
            out.println(gson.toJson("berhasil"));
            
        }
        else if(page.equals("getAllLokasi")) {
            
            
            List<LokasiModel> listLokasi = admindao.getAllLokasi();
            //convert to json
            String jsonData = gson.toJson(listLokasi);
            //tampilkan data
            out.println(jsonData);
            
        }
        
        else if(page.equals("absenNow")) {
            
            
            String userId = request.getParameter("userId");
            String kodeLokasi = request.getParameter("resultQr");
            String waktu = request.getParameter("waktu");
            String feel = request.getParameter("feel");
            
            LokasiModel lokasi = admindao.getRecordByKode(kodeLokasi);
            
            ListPresensiModel presensi = new ListPresensiModel();
            
            presensi.setIdLokasi(lokasi.getId());
            presensi.setIdUser(Integer.parseInt(userId));
            presensi.setWaktu(waktu);
            presensi.setFeel(feel);
            
            admindao.simpanDataListPresensi(presensi, "tambahPresensi");
            
            response.setContentType("application/json");
            
            ResponseModel myResponse = new ResponseModel();

            myResponse.setMessage("berhasil");
            myResponse.setData(lokasi);
            out.println(gson.toJson(myResponse));
            
        }
        
        else if(page.equals("getAllAbsen")) {
            
            
            
            List<ListPresensiModel> presensiAll = admindao.getAllPresensi();
            //convert to json
            String jsonData = gson.toJson(presensiAll);
            //tampilkan data
            out.println(jsonData);
            
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
