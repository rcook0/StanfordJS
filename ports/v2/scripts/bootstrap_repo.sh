#!/usr/bin/env bash
set -euo pipefail
: "${GIT_REMOTE_URL:=}"

if [ -z "${GIT_REMOTE_URL}" ]; then
  echo "Usage: GIT_REMOTE_URL=https://github.com/your/repo.git ./scripts/bootstrap_repo.sh"
  exit 1
fi

git init
git add .
git commit -m "chore: bootstrap sjs-core scaffold"
git branch -M main
git remote add origin "${GIT_REMOTE_URL}"
git push -u origin main || true
echo "[bootstrap] Repo initialized and remote set to ${GIT_REMOTE_URL}"
