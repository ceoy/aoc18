package com.ceoy.aoc

import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object ImageCreator {

    fun createImage(fileName: String, imageText: String) {
        val outputArray = imageText.split("\n")

        var bufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        var g2d = bufferedImage.createGraphics()
        val font = Font("Courier", Font.PLAIN, 48)
        g2d.font = font
        var fm = g2d.fontMetrics
        val width = fm.stringWidth(outputArray.maxBy {
            fm.stringWidth(it)
        })
        val height = (fm.height * outputArray.size)
        g2d.dispose()

        bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        g2d = bufferedImage.createGraphics()

        // set some stuff 😂🤣👌💯
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
        g2d.font = font
        fm = g2d.fontMetrics
        g2d.color = Color.BLACK

        outputArray.forEachIndexed { index, line ->
            val y = fm.ascent * (index + 1)
            g2d.drawString(line, 0, y)
        }

        g2d.dispose()
        try {

            // clear folder and recreate
            val folder = File("imgOut/").apply {
                if (!exists()) mkdir()
            }
            // create child fodler
            val imageFile = File(folder, "$fileName.png")
            ImageIO.write(bufferedImage, "png", imageFile)
            println("Image $fileName.png successfully created")
        } catch (ex: IOException) {
            println("Error occurred while creating $fileName.png")
            ex.printStackTrace()
        }
    }
}