$(document).ready( function (){
    var kodejurusan, kodekelas, nis, idmapel, nilaisiswa, page;
    
        function getInputValue(){
            kodejurusan = $("#kodejurusan").val();
            kodekelas = $("#kodekelas").val();
            nis = $("#nis").val();
            idmapel = $("#idmapel").val();
            nilaisiswa = $("#nilaisiswa").val();
        }
        //TAMBAH DATA
        $("#btnAdd").click(function (){
            $("#myModal").show();
            $("#titel1").show();
            $("#titel2").hide();
            $("#kodekelas, #kodejurusan, #idmapel").prop('disabled', false);
            page="tambah";
        });
        //HAPUS DATA
        $("#tabelkelas tbody").on('click', '#btnDel', function (){
            //ambil nilai nis dari baris tabel yan diselect/pilih
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            var kodekelas = baris.find("td:eq(1)").text();
            var nis = baris.find("td:eq(2)").text();
            var idmapel = baris.find("td:eq(3)").text();
            var nilaisiswa = baris.find("td:eq(4)").text();
            page = "hapus";
            
            if(confirm("Anda yakin data: ' " +nis +"-" + nilaisiswa +"'akan dihapus ?")){
                $.post("/AmirJavaMVC/NilaiCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan,
                    kodekelas: kodekelas,
                    nis: nis,
                    idmapel: idmapel,
                    nilaisiswa: nilaisiswa
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
              );
            }
        });
        //EDIT DATA
        $("#tabelkelas tbody").on('click', '#btnEdit', function (){
            $("#myModal").show();
            $("#titel1").hide();
            $("#titel2").show();
            $("#kodejurusan, #kodekelas, #idmapel").prop('disabled', true);
            
            page="tampil";
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            var kodekelas = baris.find("td:eq(1)").text();
            var nis = baris.find("td:eq(2)").text();
            var idmapel = baris.find("td:eq(3)").text();
            var nilaisiswa = baris.find("td:eq(4)").text();
            
            $("#kodejurusan").val(kodejurusan);
            $("#kodekelas").val(kodekelas);
            $("#nis") .val(nis);
            $("#idmapel") .val(idmapel);
            $("#nilaisiswa") .val(nilaisiswa);
            
//            $.post("/AmirJavaMVC/NilaiCtr",{
//                page: page,
//                kodejurusan: kodejurusan,
//                kodekelas: kodekelas,
//                nis: nis
//            },
//            function (data, status){
//                $("#kodejurusan").val(kodejurusan);
//                $("#kodekelas").val(kodekelas);
//                $("#nis") .val(nis);
//               
//                
//            }
//         );  
         page="edit";
    });
    //SIMPAN DATA
    $("#btnSave").on('click', function (){
        getInputValue();
        
        if(kodejurusan === "" && kodekelas === "" && idmapel === "" && nis === ""){
            alert("Tidak boleh ada field yang kosong!");
            if(kodejurusan === "") $("#kodejurusan").focus();
            else $("#kodekelas").focus();
        }
        else{
            $.post("/AmirJavaMVC/NilaiCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                kodekelas: kodekelas,
                nis: nis,
                idmapel: idmapel,
                nilaisiswa: nilaisiswa
                
            },
            function (data, status){
                alert(data);
                location.reload();
            }
            );
        }
    });
    //TOMBOL BATAL PADA FORM EDIT DATA
    $("#btnCancel").on('click', function (){
        $("#myModal").hide();
    });

$.ajax({
        url:"/AmirJavaMVC/NilaiCtr",      //url dari controller
        method: "GET",
        dataType: "json",
        
        success:    
            function(data){
                $("#tabelkelas").dataTable ({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns:
                    [
                        {'data': 'kodejurusan', 'name': 'kodekelas', 'type': 'string'},
                        {'data': 'kodekelas'},
                        {'data': 'nis'},
                        {'data': 'idmapel'},
                        {'data': 'nilaisiswa'},
                        {'data': null, 'className': 'dt-right', 'mRender': function(o){
                                return "<a class='btn btn-outline-success btn-sm'"
                                +"id='btnEdit'>Edit</a>"
                                +"&nbsp;&nbsp;"
                                +"<a class='btn btn-outline-danger btn-sm'"
                                +"id='btnDel'>Hapus</a>"
                        }
                    }
                    ]
                });
            }
    });
});