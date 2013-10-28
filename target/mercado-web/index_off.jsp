<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<!DOCTYPE html>
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
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Mercado - Betha Sistemas</a>
				<h:form>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li class="active"><a href="#">Fazer Compras</a></li>
							<li>
								<h:commandLink action="item">
			                        <h:outputText value="Itens"/>
			                    </h:commandLink>
							</li>
							<li>
								<h:commandLink action="lista">
			                        <h:outputText value="Lista"/>
			                    </h:commandLink>
							</li>
						</ul>
					</div>
				</h:form>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="jumbotron">
		<h2>Para o gerenciamento e controle das suas compras!</h2>
		<p class="lead">
			Utilize os menus Itens e Carrinho acima para realizar todo o cadastro
			necessário e, posteriormente, <br>o menu Fazer Compras para
			gerenciar seus produtos
		</p>
	</div>
	<div class="row">
		<h:form>
			<div class="span4">
				<h:selectOneMenu>
					<f:selectItems/>
				</h:selectOneMenu>
			</div>
			<div class="span4">
				<h:dataTable value="#{itemController.itens}" var="i"
					rendered="#{not empty itemController.itens}" border="0"
					styleClass="table table-striped" style="width:100%">
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
						<h:commandLink value="excluir" action="#{itemController.deletar}" styleClass="btn btn-danger">
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