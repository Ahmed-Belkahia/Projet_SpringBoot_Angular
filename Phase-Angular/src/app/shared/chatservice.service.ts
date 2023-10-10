import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private openaiApiKey = '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';
  private openaiApiUrl = 'https://api.openai.com/v1/engines/davinci-codex/completions';

  constructor() { }

  async getChatResponse(prompt: string): Promise<string> {
    const response = await axios.post(this.openaiApiUrl, {
      prompt,
      max_tokens: 50,
      temperature: 0.5,
      n: 1,
      stop: ['\n']
    }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.openaiApiKey}`
      }
    });
    return response.data.choices[0].text;
  }

}