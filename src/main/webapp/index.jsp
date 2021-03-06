<%@ page import="org.joda.time.DateTimeZone,org.joda.time.DateTime;" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Dreamstats</title>
		<link href="styles/all.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="tables"></div>
	
		<script type="text/javascript">
			var SERVER_TIMEZONE = <%= DateTimeZone.getDefault().getStandardOffset(new DateTime().getMillis())/1000/60 %>;
		</script>
		<script src="scripts/lib/jquery-2.0.2.min.js" type="text/javascript"></script>
		<script src="scripts/lib/my.class.min.js" type="text/javascript"></script>
		<script src="scripts/lib/lodash.min.js" type="text/javascript"></script>
		<script src="scripts/lib/microevent.js" type="text/javascript"></script>
		<script src="scripts/lib/moment.min.js" type="text/javascript"></script>
		<script src="scripts/deserializers.js" type="text/javascript"></script>
		<script src="scripts/StatsBundle.js" type="text/javascript"></script>
		<script src="scripts/StatsGraph.js" type="text/javascript"></script>
		<script src="scripts/main.js" type="text/javascript"></script>
	</body>
</html>