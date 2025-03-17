.DEFAULT_GOAL := run

.PHONY:fmt vet build
fmt:
	go fmt ./...

vet: fmt
	go vet ./...

build: vet
	go build ./cmd/main.go

run: vet
	go run ./cmd/.
