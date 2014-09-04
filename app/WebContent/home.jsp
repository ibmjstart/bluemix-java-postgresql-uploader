<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Upload</title>
  <link href="/css/bootstrap.css" rel="stylesheet">
  <link href="/css/style.css" rel="stylesheet">
  <style>
    .hero-unit {
      margin-top: 60px;
    }
  </style>
  <script src="/js/bootstrap.min.js"></script>
  <script src="/js/jquery-2.0.3.min.js"></script>
  <script src="/js/scripts.js"></script>
</head>
<meta charset="utf-8">
<body>
  <div class="container">
    <div class="hero-unit">
      <div class="text-center">
        <h1>ElephantSQL Upload Demo</h1>
        <br>
        <p>
          <form action="/upload" method="POST" enctype="multipart/form-data">
            <div class="input-group">
                <span class="input-group-btn">
                    <span class="btn btn-file">
                        Browse&hellip; <input type="file" name="file">
                    </span>
                </span>
                <input type="text" class="form-control input-file" readonly>
            </div>
            <input type="submit" class="btn btn-success" value="Upload File" />
            <a href="/delete" class="btn btn-info" title="Clear All">Clear DB</a>
          </form>
        </p>
        <% if (request.getAttribute("msg") != null) { %>
        	  <div class="alert alert-info"><%= request.getAttribute("msg") %></div>
        <% } %>
      </div>
    </div>
    <div>
      <table class="table">
      <% 
         java.util.List<String> posts = (java.util.List<String>) request.getAttribute("posts");
         if (posts == null) {
        	 posts = new ArrayList<String>();
         }
         for (String post : posts) {
      %>
        	<tr><td><%= post %></td></tr>
      <% } %>
      </table>
    </div>
  </div>
</body>
</html>
