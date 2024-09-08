# Этап сборки фронтенда
FROM node:14 AS build

# Создаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем остальные файлы в рабочую директорию
COPY . .

# Собираем фронтенд-приложение
RUN npm run build

# Этап сервировки собранного фронтенда с помощью Nginx
FROM nginx:alpine

# Копируем собранное приложение в директорию, которую обслуживает Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Копируем конфигурацию Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Открываем порт для Nginx
EXPOSE 80

# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]