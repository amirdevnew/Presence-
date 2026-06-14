/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.KelasDao;
import java.util.List;
import model.Kelas;

/**
 *
 * @author SAFARI
 */
@WebServlet(name = "KelasCtr", urlPatterns = {"/KelasCtr"})
public class KelasCtr extends HttpServlet {

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
        KelasDao klsdao = new KelasDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<Kelas> listKelas = klsdao.getAllKelas();
            //convert to json
            String jsonKelas = gson.toJson(listKelas);
            //tampilkan data
            out.println(jsonKelas);
        }
        else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String kodekelas = request.getParameter("kodekelas");
            String namakelas = request.getParameter("namakelas");
            
            if(klsdao.getRecordByKodeJurusanKelas(kodejurusan, kodekelas).getKodekelas()!= null)
            {
                response.setContentType("text/html; charset=UTF-8");
                out.println("kode kelas: " + kodekelas + " - " + klsdao.getRecordByKodeJurusanKelas(kodejurusan, kodekelas).getNamakelas()+ " sudah terpakai, OK");
            }
            else
            {
                Kelas kls = new Kelas();
                kls.setKodejurusan(request.getParameter("kodejurusan"));
                kls.setKodekelas(request.getParameter("kodekelas"));
                kls.setNamakelas(request.getParameter("namakelas"));
                
                klsdao.simpanData(kls, page);
                
                response.setContentType("text/html; charset=UTP-8");
                out.println("Data berhasil disimpan, OK");
            }
        }
        else if(page.equals("tampil")){
            String jSonKelas = gson.toJson(klsdao.getRecordByKodeJurusanKelas(request.getParameter("kodejurusan"), request.getParameter("kodekelas")));
            response.setContentType("application/json");
            out.println(jSonKelas);
        }
        else if(page.equals("edit"))
        {
            Kelas kls = new Kelas();
            kls.setKodejurusan(request.getParameter("kodejurusan"));
            kls.setKodekelas(request.getParameter("kodekelas"));
            kls.setNamakelas(request.getParameter("namakelas"));
                
            klsdao.simpanData(kls, page);
            response.setContentType("text/html; charset=UTF-8");
            out.println("Data berhasil disimpan, OK");  
        }
        else if(page.equals("hapus")){
            klsdao.hapusData(request.getParameter("kodejurusan"), request.getParameter("kodekelas"));
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
