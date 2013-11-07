<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Upload</title>
  <link href="/css/bootstrap.css" rel="stylesheet">
  <style>
    .hero-unit {
      margin-top: 60px;
    }
  </style>
  <script src="/js/bootstrap.min.js"></script>
</head>
<meta charset="utf-8">
<body>
  <div class="container">
    <div class="hero-unit">
      <div class="pull-right">    
        <a href="/delete" class="btn btn-danger" title="Clear All">X</a>
      </div>
      <div class="text-center">
        <h1><a href="/">PostgreSQL Upload</a></h1>
        <p>
          <form action="/upload" method="POST" enctype="multipart/form-data">
            <input type="file" name="file" /><br>
            <input type="submit" class="btn" value="Upload" />
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
