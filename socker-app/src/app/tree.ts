import {TreeGene} from "./tree-gene";

export class Tree {
  public length: TreeGene;
  public minLength: TreeGene;
  public lengthFactor: TreeGene;
  public widthFactor: TreeGene;
  public angleFactor: TreeGene;
  public width: TreeGene;
  public maxLevel: TreeGene;
  public selected: boolean = false;
}
