mod chunk;

use actix_web::middleware::Logger;
use actix_web::{get, post, App, HttpResponse, HttpServer, Responder};

#[get("/")]
async fn hello() -> impl Responder {
    HttpResponse::Ok().body("ayooo")
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| App::new().wrap(Logger::default()).service(hello))
        .bind(("0.0.0.0", 8080))?
        .run()
        .await
}
