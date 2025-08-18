<%@ page import="model.BookingPassenger" %>
<%
    Integer bp = (Integer)request.getAttribute("bp");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Import Passport for Booking Passenger</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/booking/passport-upload" enctype="multipart/form-data" id="importForm">
                        <input type="hidden" name="id" value="<%= bp.intValue() %>">
                        <div class="form-group">
                            <label for="passportFile">Passport File</label>
                            <input type="file" name="file" id="passportFile" class="form-control-file" accept=".pdf">
                            <small class="form-text text-muted">
                                Upload a PDF file as passport
                            </small>
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-upload mr-2"></i>Upload
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById('importForm').addEventListener('submit', function(e) {
        const passportFile = document.getElementById('passportFile').value;

        // Check if at least one file is selected
        if (!passportFile) {
            alert('Please upload the file.');
            e.preventDefault();
            return;
        }

        // Validate file extensions
        const validExtension = file => !file || file.toLowerCase().endsWith('.pdf');
        if (!validExtension(employeesFile)) {
            alert('Please upload only PDF files.');
            e.preventDefault();
        }
    });
</script>
<style>
    .card {
        border-radius: 10px;
        margin-top: 20px;
    }
    .card-header {
        border-radius: 10px 10px 0 0;
    }
    .form-control-file {
        padding: 8px;
    }
    .btn {
        border-radius: 5px;
        padding: 10px 20px;
    }
    .alert {
        border-radius: 5px;
        margin-bottom: 20px;
    }
    .alert ul {
        margin-bottom: 0;
    }
    small.form-text {
        margin-top: 5px;
    }
</style>
