$(document).ready( function (){
    var kodejurusan, namajurusan, page;
    
        function getInputValue(){
            kodejurusan = $("#kodejurusan").val();
            namajurusan = $("#namajurusan").val();
        }
        //TAMBAH DATA
        $("#btnAdd").click(function (){
            $("#myModal").show();
            $("#titel1").show();
            $("#titel2").hide();
            $("#nis").prop('disabled', false);
            page="tambah";
        });
        //HAPUS DATA
        $("#tabeljurusan tbody").on('click', '#btnDel', function (){
            //ambil nilai nis dari baris tabel yan diselect/pilih
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            var Namajurusan = baris.find("td:eq(1)").text();
            page = "hapus";
            
            if(confirm("Anda yakin data: ' " +kodejurusan +"-" + namajurusan +"'akan dihapus ?")){
                $.post("/AmirJavaMVC/JurusanCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
              );
            }
        });
        //EDIT DATA
        $("#tabeljurusan tbody").on('click', '#btnEdit', function (){
            $("#myModal").show();
            $("#titel1").hide();
            $("#titel2").show();
            $("#kodejurusan").prop('disabled', true);
            
            page="tampil";
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            
            $.post("/AmirJavaMVC/JurusanCtr",{
                page: page,
                kodejurusan: kodejurusan
            },
            function (data, status){
                $("#kodejurusan") .val(data.kodejurusan);
                $("#namajurusan") .val(data.namajurusan);
               
                
            }
         );  
         page="edit";
    });
    //SIMPAN DATA
    $("#btnSave").on('click', function (){
        getInputValue();
        
        if(kodejurusan === ""){
            alert("KODE JURUSAN harus diisi");
            $("#kodejurusan").focus();
        }
        else{
            $.post("/AmirJavaMVC/JurusanCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                namajurusan: namajurusan,
                
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
        url:"/AmirJavaMVC/JurusanCtr",      //url dari controller
        method: "GET",
        dataType: "json",
        
        success:    
            function(data){
                $("#tabeljurusan").dataTable ({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns:
                    [
                        {'data': 'kodejurusan', 'name': 'kodejurusan', 'type': 'string'},
                        {'data': 'namajurusan'},
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