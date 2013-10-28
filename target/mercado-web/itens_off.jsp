<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link href="css/estrutura.css" rel="stylesheet">
<script src="js/bootstrap.js"></script>
</head>
<body>
	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				<a class="brand" href="#">Mercado - Betha Sistemas</a>
				<h:form>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li><h:commandLink action="carrinho">
									<h:outputText value="Fazer Compras" />
								</h:commandLink></li>
							<li class="active"><a href="#">Itens</a></li>
							<li><h:commandLink action="lista">
									<h:outputText value="Lista" />
								</h:commandLink></li>
						</ul>
					</div>
				</h:form>
			</div>
		</div>
	</div>
	<div class="jumbotron">
		<h2>Para o gerenciamento e controle das suas compras!</h2>
		<p class="lead">No menu lista você tem o controle total em relação
			aos itens pretendidos.</p>
	</div>
	<div class="row">
		<h:form>
			<div class="span1"></div>
			<div class="span4">
				<table>
					<tr>
						<td><h:outputText value="Nome" />:</td>
						<td><h:inputText value="#{itemController.item.nome}" /></td>
					</tr>
					<tr>
						<td><h:outputText value="Preco" />:</td>
						<td><h:inputText value="#{itemController.item.preco}" /></td>
					</tr>
					<tr>
						<td><h:commandButton value="salvar"
								action="#{itemController.savar}" styleClass="btn btn-primary" />
						</td>
					</tr>
				</table>
			</div>
			<div class="span4">
				<h:dataTable value="#{itemController.itens}" var="i"
					rendered="#{not empty itemController.itens}" border="0"
					styleClass="table table-striped" style="width:500px">
					<h:column>
						<f:facet name="header">
							<h:outputText value="Nome" />
						</f:facet>
						<h:commandLink value="#{i.nome}" action="#{itemController.editar}">
							<f:param name="id" value="#{i.id}" />
						</h:commandLink>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Preco" />
						</f:facet>
						<h:outputText value="#{i.preco}" />

					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Excluir" />
						</f:facet>
						<h:commandLink value="excluir" action="#{itemController.deletar}"
							styleClass="btn-small btn-danger">
							<f:param name="id" value="#{i.id}" />
						</h:commandLink>
					</h:column>
				</h:dataTable>
			</div>
		</h:form>
	</div>
	<hr>
	<div id="footer">
		<div class="container">
			<p class="muted credit">
				Developed by <a href="http://calvinberschscherer.blogspot.com/">Calvin
					B. Scherer</a> for <a href="http://www.betha.com.br/">Betha
					Sistemas</a>.
			</p>
		</div>
	</div>
</body>
	</html>
</f:view>