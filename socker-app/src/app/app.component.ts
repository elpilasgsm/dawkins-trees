import {Component} from "@angular/core";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import {TreesService} from "./trees.service";
import {Tree} from "./tree";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [TreesService]
})
export class AppComponent {
  title = 'socker-app';
  targetWord = 'TEST ME';
  populationSize = 1000;
  iterations = 500;
  private serviceSocketUrl = "/socket";
  private stompClient;
  private messages = [];
  private trees: Tree[];
  private numberOfTrees = 8;
  private a: string;

  constructor(private treeService: TreesService) {
    this.initSockets();
  }

  getTrees() {
    this.treeService.get("/image?number=" + this.numberOfTrees).subscribe(value => {
        this.trees = value;
      },
      error => {
        // error - объект ошибки
      });
  }

  getChildren(tree) {
    this.treeService.get("/image/children/" + tree.length.value.toFixed(2) + "/" + tree.minLength.value.toFixed(2) + "/" + tree.lengthFactor.value.toFixed(2) + "/" + tree.widthFactor.value.toFixed(2) + "/" + tree.angleFactor.value.toFixed(2) + "/" + tree.width.value.toFixed(2) + "/" + tree.maxLevel.value.toFixed(0)).subscribe(value => {
        this.trees = value;
      },
      error => {
        // error - объект ошибки
      });
  }

  select(tree) {
    tree.selected = !tree.selected;
  }

  startGA() {
    this.stompClient.send("/app/start", {}, JSON.stringify({
      iterations: this.iterations,
      populationSize: this.populationSize,
      targetWord: this.targetWord
    }));
  }

  initSockets() {
    let ws = new SockJS(this.serviceSocketUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function (frame) {
      that.stompClient.subscribe("/topic/population", (message) => {
        if (message.body) {
          that.messages = JSON.parse(message.body);
        }
      });
    });

  }


}
