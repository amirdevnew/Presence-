//$(document).ready( function (){
//    var nis, nama, jenkel, telepon, alamat, page;
//    
//        function getInputValue(){
//            nis = $("#nis").val();
//            nama = $("#nama").val();
//            jenkel = $("#jenkel").children("option:selected").val();
//            telepon = $("#telepon").val();
//            alamat = $("#alamat").val();
//        }
//        //TAMBAH DATA
//        $("#btnAdd").click(function (){
//            $("#myModal").show();
//            $("#titel1").show();
//            $("#titel2").hide();
//            $("#nis").prop('disabled', false);
//            page="tambah";
//        });
//        //HAPUS DATA
//        $("#tabelsiswa tbody").on('click', '#btnDel', function (){
//            //ambil nilai nis dari baris tabel yan diselect/pilih
//            var baris = $(this).closest('tr');
//            var nis = baris.find("td:eq(0)").text();
//            var nama = baris.find("td:eq(1)").text();
//            page = "hapus";
//            
//            if(confirm("Anda yakin data: ' " +nis +"-" + nama +"'akan dihapus ?")){
//                $.post("/AmirJavaMVC/SiswaCtr",
//                {
//                    page: page,
//                    nis: nis
//                },
//                function(data, status){
//                    alert(data);
//                    location.reload();
//                }
//              );
//            }
//        });
//        //EDIT DATA
//        $("#tabelsiswa tbody").on('click', '#btnEdit', function (){
//            $("#myModal").show();
//            $("#titel1").hide();
//            $("#titel2").show();
//            $("#nis").prop('disabled', true);
//            
//            page="tampil";
//            var baris = $(this).closest('tr');
//            var nis = baris.find("td:eq(0)").text();
//            
//            $.post("/AmirJavaMVC/SiswaCtr",{
//                page: page,
//                nis: nis
//            },
//            function (data, status){
//                $("#nis") .val(data.nis);
//                $("#nama") .val(data.nama);
//                $("#jenkel") .val(data.jenkel);
//                $("#telepon") .val(data.telepon);
//                $("#alamat") .val(data.alamat);
//                
//            }
//         );  
//         page="edit";
//    });
//    //SIMPAN DATA
//    $("#btnSave").on('click', function (){
//        getInputValue();
//        
//        if(nis === ""){
//            alert("NIS harus diisi");
//            $("#nis").focus();
//        }
//        else if(nama === ""){
//            alert("Nama harus diisi");
//            $("#nama").focus();
//        }
//        else{
//            $.post("/AmirJavaMVC/SiswaCtr",
//            {
//                page: page,
//                nis: nis,
//                nama: nama,
//                jenkel: jenkel,
//                telepon: telepon,
//                alamat: alamat
//            },
//            function (data, status){
//                alert(data);
//                location.reload();
//            }
//            );
//        }
//    });
//    //TOMBOL BATAL PADA FORM EDIT DATA
//    $("#btnCancel").on('click', function (){
//        $("#myModal").hide();
//    });
//    
//    $.ajax({
//        url:"/AmirJavaMVC/SiswaCtr",      //url dari controller
//        method: "GET",
//        dataType: "json",
//        
//        success:    
//            function(data){
//                $("#tabelsiswa").dataTable ({
//                    serverside: true,
//                    processing: true,
//                    data: data,
//                    sort: true,
//                    searching: true,
//                    paging: true,
//                    columns:
//                    [
//                        {'data': 'nis', 'name': 'nis', 'type': 'string'},
//                        {'data': 'nama'},
//                        {'data': 'jenkel'},
//                        {'data': 'telepon'},
//                        {'data': 'alamat'},
//                        {'data': null, 'className': 'dt-right', 'mRender': function(o){
//                                return "<a class='btn btn-outline-success btn-sm'"
//                                +"id='btnEdit'>Edit</a>"
//                                +"&nbsp;&nbsp;"
//                                +"<a class='btn btn-outline-danger btn-sm'"
//                                +"id='btnDel'>Hapus</a>"
//                        }
//                    }
//                    ]
//                });
//            }
//    
//});
//});


$(document).ready(function() {
    if($('.authPage').length > 0) {
        initAuthPage();
    }
});



function initAuthPage() {
    if(isLoggedIn()) {
        var user = JSON.parse(sessionStorage.getItem('user'));
        if(user.role == 1) {
            document.location.href = '/AmirJavaMVC/pages/index.html';
        } else {
            document.location.href = '/AmirJavaMVC/admin/index.html';
        }
    }
    $('.btn-register').on('click', function () {
        
        
        if($('#username').val().length > 0 && $('#email').val().length > 0 && $('#password').val().length > 0 && $('#confirm').val().length > 0 && $('#alamat').val().length > 0) {
            
            if($("#password").val() == $('#confirm').val()) {
                $.ajax({
                    url: '/AmirJavaMVC/UserController',
                    method: 'POST',
                    data: {
                        username: $('#username').val(),
                        email: $('#email').val(),
                        password: $('#password').val(),
                        nohp: $('#nohp').val(),
                        alamat: $('#alamat').val(),
                        page: 'tambah'
                    },
                    dataType: 'JSON',
                    success: function(response) {
                        if(response == "sudah_terdaftar") {
                            alert('Email yang anda gunakan sudah terdaftar! Silahkan gunakan email yang lain atau silahkan anda login.');
                        } else if(response == "berhasil_terdaftar") {
                            alert('Berhasil terdaftar! Silahkan login..');
                            document.location.href = '/AmirJavaMVC/auth/login.html';
                        } else {
                            alert('Terjadi kesalahan dalam pemrosesan data. Untuk info lebih lanjut, silahkan anda kontak amirsmpn150@gmail.com | zeenakkikun15@gmail.com');
                            
                        }
                    },
                    error: function(err) {
                        alert('Terjadi kesalahan di sistem. Silahkan kontak developer. Kelompok 2 PBO XII RPL 1');
                        console.log(err);
                    }
                });
                
            } else {
                alert('Password dan Confirm password tidak cocok!');
            }
            
            
        } else {
            alert('Tidak boleh ada field yang kosong!');
        }
        
    });
    
    
    $(".btn-login").on('click', function () {
        if($('#email').val().length > 0 && $('#password').val().length > 0) {
            $.ajax({
                url: '/AmirJavaMVC/UserController',
                method: 'POST',
                data: {
                    email: $('#email').val(),
                    password: $('#password').val(),
                    page: 'login'
                },
                dataType: 'JSON',
                success: function(response) {
                    
                    if(response.message == "belum_terdaftar") {
                        alert('Email yang anda gunakan belum terdaftar! Jika Anda belum punya akun, silahkan Anda register terlebih dahulu.');
                    } else if(response.message == "berhasil_login") {
                        sessionStorage.setItem('user', JSON.stringify(response.data));
                        alert('Login berhasil! Selamat datang..');
                        document.location.href = '/AmirJavaMVC/pages/index.html';
                    } else if(response.message == "password_salah") {
                        alert('Password Salah! Silahkan gunakan password yang benar! Jika anda belum daftar, silahkan daftar terlebih dahulu.');
                    } else if(response.message == "empty_field") {
                        alert('Tidak boleh ada field yang kosong!');
                    } else {
                        alert('Terjadi kesalahan dalam pemrosesan data. Untuk info lebih lanjut, silahkan anda kontak amirsmpn150@gmail.com | zeenakkikun15@gmail.com');
                    }
                    console.log(response);
                },
                error: function(err) {
                    alert('Terjadi kesalahan di sistem. Silahkan kontak developer. Kelompok 2 PBO XII RPL 1');
                    console.log(err);
                }
            });
        } else {
            alert('Tidak boleh ada field yang kosong!');
        }
    });
    
}

function isLoggedIn() {
    var user = sessionStorage.getItem('user');
    if(user) {
        if(user.length > 0) {
            return true;
        }
    }
    return false;
}