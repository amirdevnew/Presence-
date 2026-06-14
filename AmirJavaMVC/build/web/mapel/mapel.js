$(document).ready( function (){
    var idmapel, namamapel, page;
    
        function getInputValue(){
            idmapel = $("#idmapel").val();
            namamapel = $("#namamapel").val();
        }
        //TAMBAH DATA
        $("#btnAdd").click(function (){
            $("#myModal").show();
            $("#titel1").show();
            $("#titel2").hide();
            $("#idmapel").prop('disabled', false);
            page="tambah";
        });
        //HAPUS DATA
        $("#tabelmapel tbody").on('click', '#btnDel', function (){
            //ambil nilai nis dari baris tabel yan diselect/pilih
            var baris = $(this).closest('tr');
            var idmapel = baris.find("td:eq(0)").text();
            var Namamapel = baris.find("td:eq(1)").text();
            page = "hapus";
            
            if(confirm("Anda yakin data: ' " +idmapel +"-" + namamapel +"'akan dihapus ?")){
                $.post("/AmirJavaMVC/MapelCtr",
                {
                    page: page,
                    idmapel: idmapel
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
              );
            }
        });
        //EDIT DATA
        $("#tabelmapel tbody").on('click', '#btnEdit', function (){
            $("#myModal").show();
            $("#titel1").hide();
            $("#titel2").show();
            $("#idmapel").prop('disabled', true);
            
            page="tampil";
            var baris = $(this).closest('tr');
            var idmapel = baris.find("td:eq(0)").text();
            
            $.post("/AmirJavaMVC/MapelCtr",{
                page: page,
                idmapel: idmapel
            },
            function (data, status){
                $("#idmapel") .val(data.idmapel);
                $("#namamapel") .val(data.namamapel);
               
                
            }
         );  
         page="edit";
    });
    //SIMPAN DATA
    $("#btnSave").on('click', function (){
        getInputValue();
        
        if(idmapel === ""){
            alert("Id mapel harus diisi");
            $("#idmapel").focus();
        }
        else{
            $.post("/AmirJavaMVC/MapelCtr",
            {
                page: page,
                idmapel: idmapel,
                namamapel: namamapel
                
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
        url:"/AmirJavaMVC/MapelCtr",      //url dari controller
        method: "GET",
        dataType: "json",
        
        success:    
            function(data){
                $("#tabelmapel").dataTable ({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns:
                    [
                        {'data': 'idmapel', 'name': 'idmapel', 'type': 'string'},
                        {'data': 'namamapel'},
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