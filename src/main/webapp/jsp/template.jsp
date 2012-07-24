<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="utf-8">
    <title><s:text name="pan.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Portal agregador de noticias">
    <meta name="author" content="Borja Bares">
    <!—[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]—>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/css/doc.css" rel="stylesheet">
  </head>
  <body>
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="brand" href="/menu"><s:text name="pan.title"/></a>
          <tiles:insertAttribute name="menu" />
          <tiles:insertAttribute name="usermenu" />
        </div>
      </div>
    </div><!-- End NavBar -->
    <div class="container content">
      <tiles:insertAttribute name="body" />
    </div><!-- End Container -->
    <div class="container-fluid upline">
	    <div class="container footer">
	      <tiles:insertAttribute name="footer" />
	    </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>