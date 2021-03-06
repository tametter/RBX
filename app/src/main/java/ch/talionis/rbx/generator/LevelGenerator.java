package ch.talionis.rbx.generator;

import ch.talionis.rbx.engine.model.Level;

import static ch.talionis.rbx.engine.model.Coordinate.coordinate;
import static ch.talionis.rbx.generator.BoardSize.boardSize;
import static ch.talionis.rbx.generator.CreateBlockArray.createBlockArray;
import static ch.talionis.rbx.generator.CreateLevel.createLevel;
import static ch.talionis.rbx.generator.DrawPath.drawPath;
import static ch.talionis.rbx.generator.RemoveEmptyBlocks.removeEmptyBlocks;
import static ch.talionis.rbx.generator.Scramble.scramble;
import static ch.talionis.rbx.generator.StartStep.startWith;

public class LevelGenerator {
    public Level generateLevel() {
        return startWith(coordinate(5, 5))
                .then(boardSize())
                .then(createBlockArray())
                .then(drawPath())
                .then(removeEmptyBlocks())
                .then(scramble())
                .then(createLevel())
                .get();
    }
}
