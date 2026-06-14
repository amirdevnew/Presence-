/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.SiswaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Siswa;


/**
 *
 * @author Amir
 */
@WebServlet(name = "SiswaCtr", urlPatterns = {"/SiswaCtr"})
public class SiswaCtr extends HttpServlet {

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
        //membuat objek siswa dao
        SiswaDao sisdao = new SiswaDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<Siswa> listSiswa = sisdao.getAllSiswa();
            //convert to json
            String jsonSiswa = gson.toJson(listSiswa);
            //tampilkan data
            out.println(jsonSiswa);
        }
        else if(page.equals("tambah")){
            String nis = request.getParameter("nis");
            String nama = request.getParameter("nama");
            
            if(sisdao.getRecordByNis(nis).getNis()!= null)
            {
                response.setContentType("text/html; charset=UTF-8");
                out.println("NIS: " + nis + " - " + sisdao.getRecordByNis(nis).getNama() + " sudah terpakai, OK");
            }
            else
            {
                Siswa sis = new Siswa();
                sis.setNis(request.getParameter("nis"));
                sis.setNama(request.getParameter("nama"));
                sis.setJenkel(request.getParameter("jenkel"));
                sis.setAlamat(request.getParameter("alamat"));
                sis.setTelepon(request.getParameter("telepon"));
                
                sisdao.simpanData(sis, page);
                
                response.setContentType("text/html; charset=UTP-8");
                out.println("Data berhasil disimpan, OK");
            }
        }
        else if(page.equals("tampil")){
            String jSonSiswa = gson.toJson(sisdao.getRecordByNis(request.getParameter("nis")));
            response.setContentType("application/json");
            out.println(jSonSiswa);
        }
        else if(page.equals("edit"))
        {
            Siswa sis = new Siswa();
            sis.setNis(request.getParameter("nis"));
            sis.setNama(request.getParameter("nama"));
            sis.setJenkel(request.getParameter("jenkel"));
            sis.setAlamat(request.getParameter("alamat"));
            sis.setTelepon(request.getParameter("telepon"));
            
            sisdao.simpanData(sis, page);
            response.setContentType("text/html; charset=UTF-8");
            out.println("Data berhasil disimpan, OK");  
        }
        else if(page.equals("hapus")){
            sisdao.hapusData(request.getParameter("nis"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus, OK");  
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