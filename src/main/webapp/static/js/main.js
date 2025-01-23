function restart() {
    $.ajax({
        url: '/restart',
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        success: function () {
            location.reload();
        }
    });
}