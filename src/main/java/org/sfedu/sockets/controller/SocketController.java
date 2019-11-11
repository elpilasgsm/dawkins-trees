package org.sfedu.sockets.controller;

import org.sfedu.sockets.ga.SimpleGA;
import org.sfedu.sockets.model.GARequest;
import org.sfedu.sockets.model.GeneTypes;
import org.sfedu.sockets.model.TreeGene;
import org.sfedu.sockets.model.TreeHromo;
import org.sfedu.sockets.utisl.DrawTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Zaporozhets on 01.11.2019.
 */
@Controller
public class SocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(value = "/image/{param-1}/{param-2}/{param-3}/{param-4}/{param-5}/{param-6}/{param-7}")
    public void image(HttpServletRequest request, HttpServletResponse response,
                      @PathVariable("param-1") Double length,
                      @PathVariable("param-2") Double minLength,
                      @PathVariable("param-3") Double lenthFactor,
                      @PathVariable("param-4") Double widthFactor,
                      @PathVariable("param-5") Double angleDelta,
                      @PathVariable("param-6") Double width,
                      @PathVariable("param-7") Integer maxLevel
    ) throws IOException {

        response.setContentType("image/png");
        BufferedImage bi = DrawTree.paint((int) (4 * GeneTypes.LENGTH.getMax()), width, length, minLength, lenthFactor, widthFactor, angleDelta, maxLevel);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();
    }


    @ResponseBody
    @RequestMapping(value = "/image/cross", method = RequestMethod.POST)
    public List<TreeHromo> crossover(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody List<TreeHromo> parents
    ) throws IOException {


        if (parents == null || CollectionUtils.isEmpty(parents) || parents.size() < 2) {
            return new ArrayList<>();
        }
        TreeHromo p1 = parents.get(0);
        TreeHromo p2 = parents.get(1);

        return Collections.singletonList(p1.crossover(p2));
    }


    @ResponseBody
    @RequestMapping(value = "/image/children/{param-1}/{param-2}/{param-3}/{param-4}/{param-5}/{param-6}/{param-7}")
    public List<TreeHromo> children(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("param-1") Double length,
                                    @PathVariable("param-2") Double minLength,
                                    @PathVariable("param-3") Double lenthFactor,
                                    @PathVariable("param-4") Double widthFactor,
                                    @PathVariable("param-5") Double angleDelta,
                                    @PathVariable("param-6") Double width,
                                    @PathVariable("param-7") Double maxLevel
    ) throws IOException {
        TreeHromo parent = new TreeHromo(length, minLength, lenthFactor, widthFactor, width, angleDelta, maxLevel);
        final List<TreeHromo> listOfGenes = new ArrayList<>();
        listOfGenes.add(new TreeHromo(new TreeGene(length, GeneTypes.LENGTH).increment(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(new TreeGene(length, GeneTypes.LENGTH).decrement(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), new TreeGene(minLength, GeneTypes.MIN_LENGTH).increment(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), new TreeGene(minLength, GeneTypes.MIN_LENGTH).decrement(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), new TreeGene(lenthFactor, GeneTypes.LENGTH_FACTOR).increment(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), new TreeGene(lenthFactor, GeneTypes.LENGTH_FACTOR).decrement(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), new TreeGene(widthFactor, GeneTypes.WIDTH_FACTOR).increment(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), new TreeGene(widthFactor, GeneTypes.WIDTH_FACTOR).decrement(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), new TreeGene(width, GeneTypes.WIDTH).increment(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), new TreeGene(width, GeneTypes.WIDTH).decrement(), parent.getAngleFactor().getValue(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), new TreeGene(angleDelta, GeneTypes.ANGLE_FACTOR).increment(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), new TreeGene(angleDelta, GeneTypes.ANGLE_FACTOR).decrement(), parent.getMaxLevel().getValue()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), new TreeGene(maxLevel, GeneTypes.MAX_LEVEL).increment()));
        listOfGenes.add(new TreeHromo(parent.getLength().getValue(), parent.getMinLength().getValue(), parent.getLengthFactor().getValue(), parent.getWidthFactor().getValue(), parent.getWidth().getValue(), parent.getAngleFactor().getValue(), new TreeGene(maxLevel, GeneTypes.MAX_LEVEL).decrement()));
        Collections.shuffle(listOfGenes);
        return listOfGenes;
    }

    @ResponseBody
    @RequestMapping(value = "/image")
    public List<TreeHromo> generate(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("number") int number
    ) throws IOException {
        final List<TreeHromo> listOfGenes = new ArrayList<>();
        if (number > 0) {
            IntStream.range(0, number).forEach(i -> listOfGenes.add(new TreeHromo()));
        }
        return listOfGenes;
    }

    @MessageMapping("/start")
    public String start(GARequest target) throws Exception {
        new SimpleGA(target.getTargetWord().toUpperCase(), target.getPopulationSize(), target.getIterations(), messagingTemplate).run();
        return "OK";
    }
}
