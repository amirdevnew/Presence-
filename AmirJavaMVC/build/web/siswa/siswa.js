$(document).ready(function() {
    if(!isLoggedIn()) {
        document.location.href = '/AmirJavaMVC/auth/login.html';
    }
    
    if(localStorage.getItem('user')) {
        $('.usernameHello').html(JSON.parse(localStorage.getItem('user')).username);
    }
    
    setInterval(function() {
        $('.dateTag').html(new Date().toLocaleString());
    }, 1000);
    
    $('.btn-logout').on('click', function() {
        localStorage.removeItem('user');
        document.location.href = '/AmirJavaMVC/auth/login.html';
    });
    
    $('.btnAbsen').on('click', function() {
        var feelToday = $('.feelToday').val();
        
        if(feelToday.length > 0) {
            $('.modalScanner').modal('show');
        }
    });
    
    $('.btnFeel').on('click', function() {
        $('.btnFeel').removeClass('active').removeClass('btn-primary').addClass('btn-danger');
        $(this).addClass('btn-primary').addClass('active').removeClass('btn-danger');
        $('.feelToday').val($(this).data('feel'));
    });
    
    var scanner = new Html5QrcodeScanner('reader', {
        width: 10,
        height: 10,
        fps: 20,
    });
        
        
    $('.modalScanner').on('show.bs.modal', function() {
        scanner.render(function(result) {
            if(result.length > 0) {
                var user = JSON.parse(localStorage.getItem('user'));
                var waktu = new Date().toISOString().slice(0, 19).replace('T', ' ');
                 $.ajax({
                     url: '/AmirJavaMVC/AdminController',
                     method: "POST",
                     data: {
                        userId: user.id,
                        resultQr: result,
                        waktu: waktu,
                        feel: $('.feelToday').val(),
                        page: 'absenNow'
                     },
                     dataType: "JSON",
                     success: function(response) {
                         if(response.message == "berhasil") {
                             console.log(response.data);
                             console.log(result);
                             Swal.fire({
                                 icon: 'success',
                                 title: 'Berhasil!',
                                 text: "Anda sudah Absen pukul " + waktu
                             }).then(function(result) {
                                 document.location.href = "/AmirJavaMVC/siswa/siswa.html";
                             });
                         } else {
                             Swal.fire({
                                 icon: 'success',
                                 title: 'Terjadi Kesalahan!',
                                 text: "Anda gagal absen"
                             }).then(function(result) {
                                 document.location.href = "/AmirJavaMVC/siswa/siswa.html";
                             });
                         }
                     }
                 });
                 scanner.clear();
            }
        });
    });
        
        
     $('.modalScanner').on('hide.bs.modal', function() {
         scanner.clear();
     });
    
});

function isLoggedIn() {
    var user = localStorage.getItem('user');
    if(user) {
        if(user.length > 0) {
            return true;
        }
    }
    return false;
}
