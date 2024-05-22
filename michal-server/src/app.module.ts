import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppSessionModule } from './app-session/app-session.module';
import { EventsModule } from './events/events.module';
import { PicturesModule } from './pictures/pictures.module';


@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',  // Change if your MySQL is on a different host
      port: 3306,         // Default MySQL port
      username: 'server',
      password: 'server123',
      database: 'server_db',
      autoLoadEntities: true,
      synchronize: true, // Set to false in production
    }),
    AppSessionModule,
    EventsModule,
    PicturesModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
