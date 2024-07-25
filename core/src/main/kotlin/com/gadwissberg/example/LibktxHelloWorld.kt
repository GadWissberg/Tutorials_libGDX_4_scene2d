package com.gadwissberg.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import ktx.actors.stage
import ktx.scene2d.Scene2dDsl
import ktx.scene2d.StageWidget
import ktx.scene2d.actors
import ktx.scene2d.image

class LibktxHelloWorld : ApplicationAdapter() {

    private val smileyTexture by lazy { Texture("smiley.png") }
    private val stage by lazy { stage() }

    override fun create() {
        Gdx.input.inputProcessor = stage
        stage.actors {
            addSmiley()
            addSmiley()
            addSmiley()
        }
    }

    private fun @Scene2dDsl StageWidget.addSmiley() {
        image(smileyTexture) {

            setOrigin(Align.center)

            putSmileyInRandomPosition(it)

            it.addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.delay(1F, Actions.scaleTo(0F, 0F, 2F)),
                        Actions.run { putSmileyInRandomPosition(it) },
                        Actions.delay(1F, Actions.scaleTo(1F, 1F, 2F)),
                        Actions.delay(4F)
                    )
                )
            )

            it.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    stage.actors {
                        addSmiley()
                    }
                    it.remove()
                }
            })
        }
    }

    private fun putSmileyInRandomPosition(it: Actor) {
        val halfWidth = it.width / 2F
        val halfHeight = it.height / 2F
        val randomX = MathUtils.random(stage.width)
        val randomY = MathUtils.random(stage.height)
        val clampedX = MathUtils.clamp(randomX, halfWidth, stage.width - halfWidth)
        val clampedY = MathUtils.clamp(randomY, halfHeight, stage.height - halfHeight)
        it.setPosition(clampedX - halfWidth, clampedY - halfHeight)
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)
        stage.act()
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
        smileyTexture.dispose()
    }

}
