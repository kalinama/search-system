$('#docForm').live('submit', function (event) {
    event.preventDefault();
    let form = $('#docForm');
    let url = form.attr("action");
    let method = form.attr("method").toUpperCase();

    let form_data = new FormData();
    let ins = document.getElementById('attachment').files.length;
    for (let x = 0; x < ins; x++) {
        form_data.append("documents", document.getElementById('attachment').files[x]);
    }
    $.ajax({
        url: url,
        type: method,
        cache: false,
        contentType: false,
        processData: false,
        data: form_data,
        success: function () {
            //    TODO: add handling
        },
        error: function (message) {
            //    TODO: add handling
        }
    });
});

$('#searchForm').live('submit', function (event) {
    event.preventDefault();
    let form = $('#searchForm');
    let url = form.attr("action");
    let method = form.attr("method").toUpperCase();

    let query = document.getElementById('query').value;

    let sendDate = (new Date()).getTime();
    $.ajax({
        url: url,
        type: method,
        dataType: 'json',
        data: ({
            messageCode: query
        }),
        success: function (response) {
            let receiveDate = (new Date()).getTime();
            let responseTime = receiveDate - sendDate;
            $('#time').html(responseTime);
            console.log(response);
        }
    });
});