$(document).ready( function (){
    var kodejurusan, namakelas, kodekelas, page;
    
        function getInputValue(){
            kodejurusan = $("#kodejurusan").val();
            kodekelas = $("#kodekelas").val();
            namakelas = $("#namakelas").val();
        }
        //TAMBAH DATA
        $("#btnAdd").click(function (){
            $("#myModal").show();
            $("#titel1").show();
            $("#titel2").hide();
            $("#kodekelas, #kodejurusan").prop('disabled', false);
            page="tambah";
        });
        //HAPUS DATA
        $("#tabelkelas tbody").on('click', '#btnDel', function (){
            //ambil nilai nis dari baris tabel yan diselect/pilih
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            var kodekelas = baris.find("td:eq(1)").text();
            var namakelas = baris.find("td:eq(2)").text();
            page = "hapus";
            
            if(confirm("Anda yakin data: ' " +kodekelas +"-" + namakelas +"'akan dihapus ?")){
                $.post("/AmirJavaMVC/KelasCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan,
                    kodekelas: kodekelas,
                    namakelas: namakelas
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
            $("#kodejurusan, #kodekelas").prop('disabled', true);
            
            page="tampil";
            var baris = $(this).closest('tr');
            var kodejurusan = baris.find("td:eq(0)").text();
            var kodekelas = baris.find("td:eq(1)").text();
            var namakelas = baris.find("td:eq(2)").text();
            
            $("#kodejurusan").val(kodejurusan);
            $("#kodekelas").val(kodekelas);
            $("#namakelas") .val(namakelas);
            
//            $.post("/AmirJavaMVC/KelasCtr",{
//                page: page,
//                kodejurusan: kodejurusan,
//                kodekelas: kodekelas,
//                namakelas: namakelas
//            },
//            function (data, status){
//                $("#kodejurusan").val(kodejurusan);
//                $("#kodekelas").val(kodekelas);
//                $("#namakelas") .val(namakelas);
//               
//                
//            }
//         );  
         page="edit";
    });
    //SIMPAN DATA
    $("#btnSave").on('click', function (){
        getInputValue();
        
        if(kodejurusan === "" && kodekelas === ""){
            alert("Kode Jurusan & Kelas harus diisi");
            if(kodejurusan === "") $("#kodejurusan").focus();
            else  $("#kodekelas").focus();
        }
        else{
            $.post("/AmirJavaMVC/KelasCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                kodekelas: kodekelas,
                namakelas: namakelas
                
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
        url:"/AmirJavaMVC/KelasCtr",      //url dari controller
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
                        {'data': 'namakelas'},
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