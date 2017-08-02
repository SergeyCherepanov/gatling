import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OroCrmSimulation extends Simulation {
  val httpProtocol = http.baseURL(sys.env.get("ORO_URL").get)
  val adminUser = sys.env.get("ORO_USER").get
  val adminPass = sys.env.get("ORO_PASSWORD").get
//  val adminUser    = "admin"
//  val adminPass    = "Admin1111"
//  val httpProtocol = http.baseURL("http://orocrm.dev")

  val headersLogin = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
  )

  val headers_1 = Map(
    "Accept" -> "text/css,*/*;q=0.1"
  )

  val headers_17 = Map(
    "Accept" -> "image/png,image/*;q=0.8,*/*;q=0.5"
  )

  val headers_18 = Map(
    "Accept" -> "application/font-woff2;q=1.0,application/font-woff;q=0.9,*/*;q=0.8",
    "Accept-Encoding" -> "identity")

  val ajaxHeadersHashNavigation = Map(
    "Accept" -> "*/*, */*; q=0.01",
    "X-CSRF-Header" -> "1",
    "X-Requested-With" -> "XMLHttpRequest",
    "x-oro-hash-navigation" -> "true")

  val ajaxHeaders = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "X-CSRF-Header" -> "1",
    "X-Requested-With" -> "XMLHttpRequest")

  val scn = scenario("OroCrmSimulation")
    .exec(http("/user/login")
      .get("/user/login")
      .headers(headersLogin)
      .check(status.is(200), css("title").find.is("Login"), css("input[name=_csrf_token]", "value").find.saveAs("csrf_token")))
    .pause(100 milliseconds)
    .exec(http("/user/login-check")
      .post("/user/login-check")
      .headers(headersLogin)
      .formParam("_username", adminUser)
      .formParam("_password", adminPass)
      .formParam("_target_path", "")
      .formParam("_csrf_token", "${csrf_token}")
      .check(status.is(200)))
    .pause(100 milliseconds)
    .exec(http("/contact")
      .get("/contact")
      .headers(ajaxHeadersHashNavigation)
      .check(status.is(200)))
    .pause(100 milliseconds)
    .exec(http("/contact/view/2049")
      .get("/contact/view/2049?grid%5Bcontacts-grid%5D=i%3D1%26p%3D25%26s%255BlastName%255D%3D-1%26s%255BfirstName%255D%3D-1%26v%3D__all__")
      .headers(ajaxHeadersHashNavigation)
      .check(status.is(200)))
    .pause(100 milliseconds)
    .exec(http("/report/static/accounts/life_time_value")
      .get("/report/static/accounts/life_time_value")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(100 milliseconds)
    .exec(http("/account/view/352")
      .get("/account/view/352?grid[orocrm_report-accounts-life_time_value]=i%3D1%26p%3D25%26s%255Bname%255D%3D-1%26v%3D__all__%26g%255BreportGroupName%255D%3Daccounts%26g%255BreportName%255D%3Dlife_time_value")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(1)
    .exec(http("/contact/view/3264")
      .get("/contact/view/3264")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(1)
    .exec(http("/salesfunnel")
      .get("/salesfunnel")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(1)
    .exec(http("/salesfunnel/view/115")
      .get("/salesfunnel/view/115?grid[sales-funnel-grid]=i%3D1%26p%3D25%26s%255BstartDate%255D%3D1%26v%3D__all__")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(3)
    .exec(http("/task")
      .get("/task")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(1)
    .exec(http("/datagrid/tasks-grid?tasks-grid-0")
      .get("/datagrid/tasks-grid?tasks-grid%5B_pager%5D%5B_page%5D=8&tasks-grid%5B_pager%5D%5B_per_page%5D=25&tasks-grid%5B_parameters%5D%5Bview%5D=__all__&tasks-grid%5B_sort_by%5D%5BdueDate%5D=ASC")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(1)
    .exec(http("/datagrid/tasks-grid?tasks-grid-1")
      .get("/datagrid/tasks-grid?tasks-grid%5B_pager%5D%5B_page%5D=8&tasks-grid%5B_pager%5D%5B_per_page%5D=25&tasks-grid%5B_parameters%5D%5Bview%5D=__all__&tasks-grid%5B_sort_by%5D%5BownerName%5D=ASC")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(1)
    .exec(http("/datagrid/tasks-grid?tasks-grid-2")
      .get("/datagrid/tasks-grid?tasks-grid%5B_pager%5D%5B_page%5D=1&tasks-grid%5B_pager%5D%5B_per_page%5D=25&tasks-grid%5B_parameters%5D%5Bview%5D=__all__&tasks-grid%5B_sort_by%5D%5BownerName%5D=ASC&tasks-grid%5B_filter%5D%5BownerName%5D%5Bvalue%5D=DIma&tasks-grid%5B_filter%5D%5BownerName%5D%5Btype%5D=1")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(1)
    .exec(http("/datagrid/tasks-grid?tasks-grid-3")
      .get("/datagrid/tasks-grid?tasks-grid%5B_pager%5D%5B_page%5D=2&tasks-grid%5B_pager%5D%5B_per_page%5D=25&tasks-grid%5B_parameters%5D%5Bview%5D=__all__&tasks-grid%5B_sort_by%5D%5BownerName%5D=ASC&tasks-grid%5B_filter%5D%5BownerName%5D%5Bvalue%5D=DIma&tasks-grid%5B_filter%5D%5BownerName%5D%5Btype%5D=1")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(941 milliseconds)
    .exec(http("request_48")
      .get("/datagrid/tasks-grid?tasks-grid%5B_pager%5D%5B_page%5D=3&tasks-grid%5B_pager%5D%5B_per_page%5D=25&tasks-grid%5B_parameters%5D%5Bview%5D=__all__&tasks-grid%5B_sort_by%5D%5BownerName%5D=ASC&tasks-grid%5B_filter%5D%5BownerName%5D%5Bvalue%5D=DIma&tasks-grid%5B_filter%5D%5BownerName%5D%5Btype%5D=1")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(1)
    .exec(http("request_49")
      .get("/task/view/93?grid[tasks-grid]=i%3D3%26p%3D25%26s%255BownerName%255D%3D-1%26f%255BownerName%255D%255Bvalue%255D%3DDIma%26f%255BownerName%255D%255Btype%255D%3D1%26v%3D__all__")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(212 milliseconds)
    .exec(http("/api/rest/latest/relation/OroCRM_Bundle_TaskBundle_Entity_Task/93/comment?limit=5")
      .get("/api/rest/latest/relation/OroCRM_Bundle_TaskBundle_Entity_Task/93/comment?limit=5")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(4)
    .exec(http("/calendar/event")
      .get("/calendar/event")
      .headers(ajaxHeadersHashNavigation).check(status.is(200)))
    .pause(43)
    .exec(http("/datagrid/calendar-event-grid")
      .get("/datagrid/calendar-event-grid?calendar-event-grid%5B_pager%5D%5B_page%5D=200&calendar-event-grid%5B_pager%5D%5B_per_page%5D=25&calendar-event-grid%5B_parameters%5D%5Bview%5D=__all__&calendar-event-grid%5B_sort_by%5D%5Bstart%5D=ASC")
      .headers(ajaxHeaders).check(status.is(200)))
    .pause(21)
    .exec(http("/user/logout")
      .get("/user/logout")
      .headers(headersLogin))

  setUp(scn.inject(atOnceUsers(10), rampUsers(20) over (30 seconds))).protocols(httpProtocol)
}

