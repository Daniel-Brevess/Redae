# Base development image for the frontend build.
# Backend development is defined in backend/Dockerfile and docker-compose.yml.
FROM node:22-alpine AS frontend
WORKDIR /app
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ ./
ARG VITE_EMAIL_VERIFICATION_ENABLED=false
ENV VITE_EMAIL_VERIFICATION_ENABLED=$VITE_EMAIL_VERIFICATION_ENABLED
ARG VITE_API_URL=http://192.168.100.9:8080/api/v1
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

FROM nginx:alpine
COPY --from=frontend /app/dist /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
