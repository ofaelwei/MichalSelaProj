import { Test, TestingModule } from '@nestjs/testing';
import { AppSessionController } from './app-session.controller';

describe('AppSessionController', () => {
  let controller: AppSessionController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [AppSessionController],
    }).compile();

    controller = module.get<AppSessionController>(AppSessionController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
