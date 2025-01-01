#
# Build stage
#
FROM --platform=$BUILDPLATFORM rust:1.65 AS build

RUN /bin/sh -c "apt-get update && apt-get install -y g++-aarch64-linux-gnu libc6-dev-arm64-cross"

RUN rustup target add aarch64-unknown-linux-gnu

# Copy files over
COPY square_data/Cargo.toml square_data/Cargo.lock /app/
Copy square_data/src /app/src

# Build
WORKDIR /app
RUN cargo build --release --target=aarch64-unknown-linux-gnu --config target.aarch64-unknown-linux-gnu.linker=\"aarch64-linux-gnu-gcc\"

#
# Package stage
#
FROM debian:buster-slim AS app
COPY --from=build /app/target/aarch64-unknown-linux-gnu/release/square_data /my-app
EXPOSE 8080
CMD ["/my-app"]