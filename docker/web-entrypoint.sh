#!/bin/sh
set -eu

echo "[web] waiting briefly for MySQL..."
sleep 8

echo "[web] synchronizing database schema..."
pnpm db:push

echo "[web] starting application..."
exec pnpm start