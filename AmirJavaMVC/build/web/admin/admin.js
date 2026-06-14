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
    
    if($('.tambahLokasiPage').length > 0) {
        $('.simpanLokasi').on('click', addLokasi);
    }
    
    getAllAbsen();
    
    
    
    
});

function isLoggedIn() {
    var user = localStorage.getItem('user');
    if(user) {
                try {
                    if(JSON.parse(user) && JSON.parse(user).role == 1) {
                        return true;
                    }
                } catch(error) {
                    console.log("Error occured on parsing data");
                }
    }
    return false;
}

function addLokasi() {
    var namaLokasi = $('.namaLokasi').val();
    if(namaLokasi.length > 0) {
        $.ajax({
            url: '/AmirJavaMVC/AdminController',
            method: 'POST',
            data: {
                namaLokasi: namaLokasi,
                kodeLokasi: uniqid("Lok_"),
                page: 'tambahLokasi'
            },
            dataType: 'JSON',
            success: function(response) {
                if(response == "berhasil") {
                    alert("Lokasi berhasil ditambahkan!");
                    document.location.href = '/AmirJavaMVC/admin/admin.html';
                } else {
                    alert("Lokasi gagal ditambahkan!");
                }
            }
        });
    } else {
        alert('Lokasi dibawah batas minimal!');
    }
}

function getAllAbsen() {
    console.log("oke");
    $.ajax({
        url: '/AmirJavaMVC/AdminController',
        method: 'POST',
        data: {
            page: 'getAllAbsen'
        },
        dataType: 'JSON',
        success: function(response) {
            if(response) {
                console.log(response);
                var el = "";
                var no = 1;
                response.forEach(function(presensi) {
                    el = el + "<tr><td>"+ no++ +"</td> <td>"+ presensi.namaUser +"</td>   <td>"+ presensi.namaLokasi +"</td>  <td>"+ presensi.feel +"</td>   <td>"+ presensi.waktu +"</td></tr>";
                });
                $('.body-presensi').html(el);
            }
        }
    });
}

function uniqid(prefix = "", random = false) {
    const sec = Date.now() * 1000 + Math.random() * 1000;
    const id = sec.toString(16).replace(/\./g, "").padEnd(14, "0");
    return `${prefix}${id}${random ? `.${Math.trunc(Math.random() * 100000000)}`:""}`;
};