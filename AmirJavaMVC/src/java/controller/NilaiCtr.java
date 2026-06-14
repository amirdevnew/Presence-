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
import dao.NilaiDao;
import java.util.List;
import model.Nilai;

/**
 *
 * @author SAFARI
 */
@WebServlet(name = "NilaiCtr", urlPatterns = {"/NilaiCtr"})
public class NilaiCtr extends HttpServlet {

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
        
        
        //respon berupa data json
        response.setContentType("application/json");
        //mengambil nilai parameter page
        String page = request.getParameter("page");
        //menampilkan data
        PrintWriter out = response.getWriter();
        //membuat objek siswa dao
        NilaiDao nilaidao = new NilaiDao();
        //membuat objek Gson
        Gson gson = new Gson();
        
        if(page == null){ //viewer mengirimkan nilai parameter page adalah null
            List<Nilai> listNilai = nilaidao.getAllNilai();
            //convert to json
            String jsonNilai = gson.toJson(listNilai);
            //tampilkan data
            out.println(jsonNilai);
        }
        else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String kodekelas = request.getParameter("kodekelas");
            String  nis = request.getParameter("nis");
            String idmapel = request.getParameter("idmapel");
            String nilaisiswa = request.getParameter("nilaisiswa");
            
            if(nilaidao.getRecordByKodeJurusanKelasNisMapel(kodejurusan, kodekelas, nis, idmapel).getNis()!= null)
            {
                response.setContentType("text/html; charset=UTF-8");
                out.println("Nis: " + nis + " - " + nilaidao.getRecordByKodeJurusanKelasNisMapel(kodejurusan, kodekelas, nis, idmapel).getKodekelas()+ " sudah terpakai, OK");
            }
            else
            {
                Nilai nilai = new Nilai();
                nilai.setKodejurusan(kodejurusan);
                nilai.setKodekelas(kodekelas);
                nilai.setNis(nis);
                nilai.setIdmapel(idmapel);
                nilai.setNilaisiswa(nilaisiswa);
                
                nilaidao.simpanData(nilai, page);
                
                response.setContentType("text/html; charset=UTP-8");
                out.println("Data berhasil disimpan, OK");
            }
        }
        else if(page.equals("tampil")){
            String jSonNilai = gson.toJson(nilaidao.getRecordByKodeJurusanKelasNisMapel(request.getParameter("kodejurusan"), request.getParameter("kodekelas"), request.getParameter("nis"), request.getParameter("idmapel")));
            response.setContentType("application/json");
            out.println(jSonNilai);
        }
        else if(page.equals("edit"))
        {
            String kodejurusan = request.getParameter("kodejurusan");
            String kodekelas = request.getParameter("kodekelas");
            String  nis = request.getParameter("nis");
            String idmapel = request.getParameter("idmapel");
            String nilaisiswa = request.getParameter("nilaisiswa");
            
            Nilai nilai = new Nilai();
            nilai.setKodejurusan(kodejurusan);
            nilai.setKodekelas(kodekelas);
            nilai.setNis(nis);
            nilai.setIdmapel(idmapel);
            nilai.setNilaisiswa(nilaisiswa);
                
            nilaidao.simpanData(nilai, page);
            response.setContentType("text/html; charset=UTF-8");
            out.println("Data berhasil disimpan, OK");  
        }
        else if(page.equals("hapus")){
            nilaidao.hapusData(request.getParameter("kodejurusan"), request.getParameter("kodekelas"), request.getParameter("nis"), request.getParameter("idmapel"));
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
