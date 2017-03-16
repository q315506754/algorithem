var gulp = require('gulp');
var exec = require('child_process').exec;
const uglify = require('gulp-uglify');
const rename = require('gulp-rename');

gulp.task('minimize', ['compile'], function () {
    let options = {
        // sourceMap: true,
        // sourceMapIncludeSources: true,
        // sourceMapRoot: './src/',
        // mangle: true,
        compress: {
            sequences: true,
            dead_code: true,
            conditionals: true,
            booleans: true,
            unused: true,
            if_return: true,
            join_vars: true
        }
    };

    return gulp.src('target/bundle.js')
        .pipe(rename({extname: '.min.js'}))
            .pipe(uglify(options))
        .pipe(gulp.dest('./target/'));
          ;
});

gulp.task('compile', function(cb) {
    // 将你的默认的任务代码放在这
  console.log('default task start...');
  exec("npm run build",function(err){
	  exec("browserify target/myTool.js -o target/bundle.js",function(err){
          cb()
		});
  });
  
});

gulp.task('run', function (cb) {
	exec("start babel-node src/myTool.js",function(err){
	  cb();
	});
});

gulp.task('watch', function (cb) {
    let gulpWatcher = gulp.watch(['src/**/*.js']);
	
			
    gulpWatcher.on('change', function (e) {
        if (e.type === 'changed' ) {
            console.log('changed');
			
			 exec("start babel-node src/myTool.js",function(err){
			 //exec("start babel_node run directly.bat\"",function(err){
				//cb()
			});
        }
    });

   
});

gulp.task('default', ['compile', 'minimize']);