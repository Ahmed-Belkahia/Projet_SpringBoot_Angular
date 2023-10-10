import { Component, OnInit } from '@angular/core';

import { ChatService } from 'src/app/shared/chatservice.service';
@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})



export class ChatbotComponent {

  chatInput = '';
  chatHistory: { message: string, from: 'user' | 'chatbot' }[] = [];

  constructor(private chatService: ChatService) {}

  async sendMessage() {
    const message = this.chatInput.trim();
    if (message) {
      this.chatHistory.push({ message, from: 'user' });
      this.chatInput = '';
      const response = await this.chatService.getChatResponse(message);
      this.chatHistory.push({ message: response.trim(), from: 'chatbot' });
    }
  }

}