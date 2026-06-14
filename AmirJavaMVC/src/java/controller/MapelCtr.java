/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.MapelDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.mapel;


/**
 *
 * @author User
 */
@WebServlet(name = "MapelCtr", urlPatterns = {"/MapelCtr"})
public class MapelCtr extends HttpServlet {

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
        MapelDao madao = new MapelDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<mapel> listMapel = madao.getAllMapel();
            //convert to json
            String jsonMapel = gson.toJson(listMapel);
            //tampilkan data
            out.println(jsonMapel);
        }
        else if(page.equals("tambah")){
            String idmapel = request.getParameter("idmapel");
            String namamapel = request.getParameter("namamapel");
            
            if(madao.getRecordByIdMapel(idmapel).getIdmapel()!= null)
            {
                response.setContentType("text/html; charset=UTF-8");
                out.println("Id mapel: " + idmapel + " - " + madao.getRecordByIdMapel(idmapel).getNamamapel() + " sudah terpakai, OK");
            }
            else
            {
                mapel ma = new mapel();
                ma.setIdmapel(request.getParameter("idmapel"));
                ma.setNamamapel(request.getParameter("namamapel"));
                
                madao.simpanData(ma, page);
                
                response.setContentType("text/html; charset=UTP-8");
                out.println("Data berhasil disimpan, OK");
            }
        }
        else if(page.equals("tampil")){
            String jSonMapel = gson.toJson(madao.getRecordByIdMapel(request.getParameter("idmapel")));
            response.setContentType("application/json");
            out.println(jSonMapel);
        }
        else if(page.equals("edit"))
        {
            mapel ma = new mapel();
            ma.setIdmapel(request.getParameter("idmapel"));
            ma.setNamamapel(request.getParameter("namamapel"));
                
            madao.simpanData(ma, page);
            response.setContentType("text/html; charset=UTF-8");
            out.println("Data berhasil disimpan, OK");  
        }
        else if(page.equals("hapus")){
            madao.hapusData(request.getParameter("idmapel"));
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