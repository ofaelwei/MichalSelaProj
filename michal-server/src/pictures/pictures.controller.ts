import { Controller, Get, Post, Body, UploadedFile, UseInterceptors } from '@nestjs/common';
import { FileInterceptor } from '@nestjs/platform-express';
import { PicturesService } from './pictures.service';
import { Picture } from './picture.entity';
import { Express } from 'express';  // Import Express types
import { Multer } from 'multer';    // Import Multer types

@Controller('pictures')
export class PicturesController {
  constructor(private readonly picturesService: PicturesService) {}

  @Post('upload')
  @UseInterceptors(FileInterceptor('photo'))
  async uploadPicture(
    @Body('userId') userId: string,
    @Body('timestamp') timestamp: string,
    @UploadedFile() photo: Express.Multer.File,  // Use Multer's file type from Express
  ): Promise<Picture> {
    const buffer = photo.buffer;
    const date = new Date(timestamp);
    return this.picturesService.create(userId, date, buffer);
  }

  @Get()
  findAll(): Promise<Picture[]> {
    return this.picturesService.findAll();
  }
}
