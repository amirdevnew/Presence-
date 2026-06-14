/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.JurusanDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.jurusan;


/**
 *
 * @author User
 */
@WebServlet(name = "JurusanCtr", urlPatterns = {"/JurusanCtr"})
public class JurusanCtr extends HttpServlet {

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
        JurusanDao jurdao = new JurusanDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<jurusan> listJurusan = jurdao.getAllJurusan();
            //convert to json
            String jsonJurusan = gson.toJson(listJurusan);
            //tampilkan data
            out.println(jsonJurusan);
        }
        else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String namajurusan = request.getParameter("namajurusan");
            
            if(jurdao.getRecordByKodeJurusan(kodejurusan).getKodejurusan()!= null)
            {
                response.setContentType("text/html; charset=UTF-8");
                out.println("Kode jurusan: " + kodejurusan + " - " + jurdao.getRecordByKodeJurusan(kodejurusan).getNamajurusan() + " sudah terpakai, OK");
            }
            else
            {
                jurusan jur = new jurusan();
                jur.setKodejurusan(request.getParameter("kodejurusan"));
                jur.setNamajurusan(request.getParameter("namajurusan"));
                
                jurdao.simpanData(jur, page);
                
                response.setContentType("text/html; charset=UTP-8");
                out.println("Data berhasil disimpan, OK");
            }
        }
        else if(page.equals("tampil")){
            String jSonJurusan = gson.toJson(jurdao.getRecordByKodeJurusan(request.getParameter("kodejurusan")));
            response.setContentType("application/json");
            out.println(jSonJurusan);
        }
        else if(page.equals("edit"))
        {
            jurusan jur = new jurusan();
            jur.setKodejurusan(request.getParameter("kodejurusan"));
            jur.setNamajurusan(request.getParameter("namajurusan"));
                
            jurdao.simpanData(jur, page);
            response.setContentType("text/html; charset=UTF-8");
            out.println("Data berhasil disimpan, OK");  
        }
        else if(page.equals("hapus")){
            jurdao.hapusData(request.getParameter("kodejurusan"));
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