

$(document).ready(function() {
    if($('.authPage').length > 0) {
        initAuthPage();
    }
});



function initAuthPage() {
    if(isLoggedIn()) {
        var user = JSON.parse(localStorage.getItem('user'));
        if(user.role == 1) {
            document.location.href = '/AmirJavaMVC/admin/admin.html';
        } else {
            document.location.href = '/AmirJavaMVC/siswa/siswa.html';
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
                        localStorage.setItem('user', JSON.stringify(response.data));
                        alert('Login berhasil! Selamat datang..');
                        
                        if(response.data.role == 1) {
                            document.location.href = '/AmirJavaMVC/admin/admin.html';
                        } else {
                            document.location.href = '/AmirJavaMVC/siswa/siswa.html';
                        }
         
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
    var user = localStorage.getItem('user');
    if(user) {
        if(user.length > 0) {
            return true;
        }
    }
    return false;
}